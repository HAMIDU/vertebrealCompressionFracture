package com.karizma.onlineshopping.purchase.service.impl;

import com.karizma.onlineshopping.base.OnlineShoppingPropertiesWrapper;
import com.karizma.onlineshopping.base.exception.CoreException;
import com.karizma.onlineshopping.base.exception.FaultCode;
import com.karizma.onlineshopping.iam.entity.UserProfile;
import com.karizma.onlineshopping.iam.repository.UserProfileRepository;
import com.karizma.onlineshopping.product.enumeration.PackingType;
import com.karizma.onlineshopping.product.repository.ProductConfigRepository;
import com.karizma.onlineshopping.product.repository.ProductRepository;
import com.karizma.onlineshopping.purchase.dto.BasketDto;
import com.karizma.onlineshopping.purchase.dto.PurchaseChangeStatusDto;
import com.karizma.onlineshopping.purchase.dto.PurchaseFinalizeDto;
import com.karizma.onlineshopping.purchase.entity.*;
import com.karizma.onlineshopping.purchase.enumeration.BasketStatus;
import com.karizma.onlineshopping.purchase.enumeration.DeliveryStatus;
import com.karizma.onlineshopping.purchase.enumeration.DeliveryType;
import com.karizma.onlineshopping.purchase.enumeration.PurchaseStatus;
import com.karizma.onlineshopping.purchase.repository.BasketProductRepository;
import com.karizma.onlineshopping.purchase.repository.BasketRepository;
import com.karizma.onlineshopping.purchase.repository.PocketRepository;
import com.karizma.onlineshopping.purchase.repository.PurchaseRepository;
import com.karizma.onlineshopping.purchase.service.PurchaseService;
import com.karizma.onlineshopping.purchase.service.totalamount.FactoryTotalAmount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.karizma.onlineshopping.base.ValidationUtility.invalidPurchaseTime;
import static org.hibernate.type.IntegerType.ZERO;

@Service
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final BasketRepository basketRepository;
    private final UserProfileRepository userProfileRepository;
    private final BasketProductRepository basketProductRepository;
    private final ProductRepository productRepository;
    private final ProductConfigRepository productConfigRepository;
    private final OnlineShoppingPropertiesWrapper onlineShoppingPropertiesWrapper;
    private final PocketRepository pocketRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, BasketRepository basketRepository,
                               UserProfileRepository userProfileRepository, BasketProductRepository basketProductRepository,
                               ProductRepository productRepository, ProductConfigRepository productConfigRepository, OnlineShoppingPropertiesWrapper onlineShoppingPropertiesWrapper, PocketRepository pocketRepository) {
        this.purchaseRepository = purchaseRepository;
        this.basketRepository = basketRepository;
        this.userProfileRepository = userProfileRepository;
        this.basketProductRepository = basketProductRepository;
        this.productRepository = productRepository;
        this.productConfigRepository = productConfigRepository;
        this.onlineShoppingPropertiesWrapper = onlineShoppingPropertiesWrapper;
        this.pocketRepository = pocketRepository;
    }

    @Override
    @Transactional
    public Basket createNewBasket(List<BasketDto> basketDtos, Long userProfileId) {


        var userProfile = currentUserProfile(userProfileId);

        if (invalidPurchaseTime()) {
            throw new CoreException(FaultCode.COULD_NOT_PURCHASE_NOW);
        }

        var ref = new Object() {
            long minimumPurchasePrice = 0;
        };

        var basket = basketRepository.save(Basket.builder().reservationDate(LocalDateTime.now())
                .status(BasketStatus.PENDING)
                .userProfile(userProfile)
                .build());

        basketDtos.stream().forEach(c -> {

            productRepository.findByIdAndActive(c.getProductId(), Boolean.TRUE).orElseThrow(() ->
                    new CoreException(FaultCode.NOT_FOUND_ACTIVE_PRODUCT));

            var productConfig = productConfigRepository.findByProductIdAndActive(
                    c.getProductId(), Boolean.TRUE)
                    .orElseThrow(() -> new CoreException(FaultCode.NOT_FOUND_PRODUCT_DETAILS));

            var neededProductCount = Math.subtractExact(productConfig.getAvailableProductCount(), c.getCount());

            if (neededProductCount < ZERO) {
                throw new CoreException(FaultCode.PRODUCT_NOT_EXISTS_ENOUGH);
            }

            productConfig.setAvailableProductCount(neededProductCount);
            productConfigRepository.save(productConfig);

            var factoryTotalAmount = new FactoryTotalAmount();

            long totalPricePerProduct = factoryTotalAmount.getContext(productConfig.getDiscountType())
                    .executeStrategy(productConfig.getUnitPrice(), c.getCount(), productConfig.getDiscount());

            ref.minimumPurchasePrice += totalPricePerProduct;

            basketProductRepository.save(BasketProduct.builder().basketProductId(BasketProductId.builder()
                    .basketId(basket.getId())
                    .productId(c.getProductId()).build())
                    .totalCount(c.getCount())
                    .totalPrice(totalPricePerProduct)
                    .build());

        });

        if (ref.minimumPurchasePrice < onlineShoppingPropertiesWrapper.getMinTotalReceiptAmount()) {
            throw new CoreException(FaultCode.TOTAL_AMOUNT_IS_LESS_THAN_MINIMUM_VALID_PRICE);
        }

        return basket;
    }

    @Transactional
    @Override
    public Purchase createNewPurchase(Basket basket, Long userProfileId) {


        currentUserProfile(userProfileId);

        if (invalidPurchaseTime()) {
            throw new CoreException(FaultCode.COULD_NOT_PURCHASE_NOW);
        }

        basket = basketRepository.findById(basket.getId()).orElseThrow(() -> new CoreException(FaultCode.INVALID_BASKET));
        if (!basket.getUserProfile().getId().equals(userProfileId)) {
            throw new CoreException(FaultCode.USER_IS_NOT_ENABLED);
        }
        var basketProducts = basketProductRepository.findByBasket(basket);

        var receiptAmount = basketProducts.stream()
                .map(x -> x.getTotalPrice())
                .collect(Collectors.summarizingLong(Long::longValue));


        basket.setStatus(BasketStatus.SUCCESSFUL);
        basketRepository.save(basket);

        return purchaseRepository.save(Purchase.builder().id(basket.getId()).status(PurchaseStatus.COMPLETING)
                .receiptAmount(receiptAmount.getSum()).build());

    }

    @Transactional
    @Override
    public Purchase purchaseFinalize(PurchaseFinalizeDto purchaseFinalizeDto, Long userProfileId) {

        currentUserProfile(userProfileId);
        var currentPurchase = purchaseRepository.findById(purchaseFinalizeDto.getPurchaseId())
                .orElseThrow(() -> new CoreException(FaultCode.INVALID_PURCHASE));
        currentPurchase.setLastUpdate(LocalDateTime.now());
        currentPurchase.setStatus(purchaseFinalizeDto.getStatus());
        if (currentPurchase.getDestinationAddress() == null)
            currentPurchase.setDestinationAddress(purchaseFinalizeDto.getDestinationAddress().strip());

        var currentPurchaseProducts = basketProductRepository.findByBasket(currentPurchase.getBasket());

        HashSet<Pocket> vipDelivery = new HashSet<>();
        HashSet<Pocket> regularDelivery = new HashSet<>();


        currentPurchaseProducts.stream().filter(product ->
                product.getProduct().getPackingType().equals(PackingType.FRAGILE))
                .forEach(c -> {
                    vipDelivery.add(Pocket.builder().purchase(currentPurchase)
                            .deliveryDate(purchaseFinalizeDto.getVipDeliveryDate())
                            .status(DeliveryStatus.PREPARING)
                            .deliveryType(DeliveryType.VIP)
                            .build());

                });
        currentPurchaseProducts.stream().filter(product ->
                product.getProduct().getPackingType().equals(PackingType.REGULAR))
                .forEach(c -> {
                    regularDelivery.add(Pocket.builder().purchase(currentPurchase)
                            .deliveryDate(purchaseFinalizeDto.getRegularDeliveryDate())
                            .status(DeliveryStatus.PREPARING)
                            .deliveryType(DeliveryType.ORDINARY)
                            .build());

                });

        pocketRepository.saveAll(regularDelivery);
        pocketRepository.saveAll(vipDelivery);

        return purchaseRepository.save(currentPurchase);

    }

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60 * 5)/*Run Daily periodic, Interval 5 minutes*/
    @Override
    public synchronized void cancelledIncompletePurchase() {

        var notComplementedPurchases = purchaseRepository.findByStatus(PurchaseStatus.COMPLETING);
        List<Basket> notComplementedPurchaseBasket = new LinkedList<>();

        notComplementedPurchases.stream().filter(purchase -> ChronoUnit.MINUTES.between(purchase.getPurchaseDate(),
                LocalDateTime.now()) > onlineShoppingPropertiesWrapper.getMaxOnGoingPurchaseMinutes())
                .forEach(purchase -> {
                    purchase.setStatus(PurchaseStatus.CANCELED);
                    purchase.setLastUpdate(LocalDateTime.now());
                    purchase.getBasket().setStatus(BasketStatus.CANCELED);
                    notComplementedPurchaseBasket.add(purchase.getBasket());
                });
        //todo must be impl {return reserved product to available product list in ProductConfig Table}
        basketRepository.saveAll(notComplementedPurchaseBasket);
        purchaseRepository.saveAll(notComplementedPurchases);

    }

    @Override
    public Purchase changePurchaseStatus(PurchaseChangeStatusDto purchaseChangeStatusDto) {
        var purchase = purchaseRepository.findById(purchaseChangeStatusDto.getPurchaseId()).orElseThrow(() ->
                new CoreException(FaultCode.INVALID_PURCHASE));

        if (!purchase.getStatus().equals(purchaseChangeStatusDto.getStatus())) {
            purchase.setStatus(purchaseChangeStatusDto.getStatus());
            purchase.setPurchaseDate(LocalDateTime.now());
            purchaseRepository.save(purchase);
        }
        return purchase;
    }

    private UserProfile currentUserProfile(Long userProfileId) {
        return userProfileRepository.findById(userProfileId).orElseThrow(
                () -> new CoreException(FaultCode.NO_CUSTOMER_FOUND));
    }
}
