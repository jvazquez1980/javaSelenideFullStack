package pages.home;

public class SauceDemoPage {

    public static class Login {
        public static final String userName = "[data-test=\"username\"]";
        public static final String password = "[data-test=\"password\"]";
        public static final String loginCta = "#login-button";
        public static final String errorMessage = "[data-test=\"error\"]";
    }

    public static class Home {
        public static final String cartIcon = "[data-test=\"shopping-cart-link\"]";
        public static final String burgerMenu = "#react-burger-menu-btn";
        public static final String burgerMenuOptions = "[data-test=\"open-menu\"]";
        public static final String resetAppState = "#reset_sidebar_link";
        public static final String productList = "[data-test=\"inventory-list\"]";
        public static final String productItem = "[data-test=\"inventory-item\"]";
        public static final String addToCartButton = "[data-test^=\"add-to-cart\"]";
        public static final String allTestProduct = "[data-test^=\"remove-test\"]";
        public static final String removeToCartButton = "button[id^=\"remove\"]";
        public static final String logout = "#logout_sidebar_link";
        public static final String shortSelect = "[data-test=\"product-sort-container\"]";
    }

    public static class Product {
        public static final String productImage = "img[src$=\".jpg\"]";
        public static final String productTitle = "[data-test=\"inventory-item-name\"]";
        public static final String productDescription = "[data-test=\"inventory-item-desc\"]";
        public static final String productPrice = "[data-test=\"inventory-item-price\"]";
        public static final String productDetails = "[data-test=\"inventory-item-description\"]";
        public static final String remove = "[id^=\"remove\"]";
        public static final String add = "#add-to-cart";
    }

    public static class Cart {
        public static final String cartWithProducts = "[data-test=\"shopping-cart-badge\"]";
        public static final String emptyCart = "[data-test=\"shopping-cart-link\"]";
    }

    public static class Checkout {
        public static final String checkoutButton = "#checkout";
        public static final String firstName = "#first-name";
        public static final String lastName = "#last-name";
        public static final String zipCode = "#postal-code";
        public static final String continueButton = "[data-test=\"continue\"]";
        public static final String paymentInfo = "[data-test=\"payment-info-label\"]";
        public static final String paymentValue = "[data-test=\"payment-info-value\"]";
        public static final String shippingInfo = "[data-test=\"shipping-info-label\"]";
        public static final String shippingValue = "[data-test=\"shipping-info-value\"]";
        public static final String totalInfo = "[data-test=\"total-info-label\"]";
        public static final String subtotalValue = "[data-test=\"subtotal-label\"]";
        public static final String taxValue = "[data-test=\"tax-label\"]";
        public static final String totalValue = "[data-test=\"total-label\"]";
        public static final String finish = "#finish";
        public static final String succesMessage = "[data-test=\"secondary-header\"]";
        public static final String thankYouPage = "[data-test=\"checkout-complete-container\"]";
    }
}
