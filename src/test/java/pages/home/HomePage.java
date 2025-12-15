package pages.home;

/**
 * HomePage - Contains only page locators for BDD approach
 * No actions, only selectors that will be used by Steps classes
 */
public class HomePage {

    // Page locators - Only selectors, no actions
    public static final String PAGE_BANNER = "body";

    // IDs (sin prefijo #)
    public static final String ROOMS_SECTION_ID = "rooms";
    public static final String location = "#location";
    public static final String CONTACT_SECTION_ID = "contact";
    public static final String SUBMIT_BUTTON_ID = "submitContact";
    public static final String booking = "booking";

    // Data attributes
    public static final String NAME_FIELD = "[data-testid='ContactName']";
    public static final String EMAIL_FIELD = "[data-testid='ContactEmail']";
    public static final String PHONE_FIELD = "[data-testid='ContactPhone']";
    public static final String SUBJECT_FIELD = "[data-testid='ContactSubject']";
    public static final String MESSAGE_FIELD = "[data-testid='ContactDescription']";

    // Button selectors
    public static final String BOOK_ROOM_BUTTON_TEXT = "Check Availability";
    public static final String BOOK_ROOM_BUTTON_CSS = ".btn.btn-primary.w-100.py-2";
    public static final String SUBMIT_BUTTON_TEXT = "Submit";
    public static final String SUBMIT_BUTTON_CSS = "button[type='button']";
    public static final String bookNowButton = ".btn.btn-primary";
    public static final String calendar = ".card-body";
    public static final String selected = "[title=\"Selected\"]";
    public static final String reserveNowButton = "#doReservation";
    public static final String name = "[name='firstname']";
    public static final String surname = "[name='lastname']";
    public static final String email = "[name='email']";
    public static final String phone = "[name='phone']";
}
