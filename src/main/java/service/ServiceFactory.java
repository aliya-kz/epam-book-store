package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;


import static service.ServiceConstants.*;

public class ServiceFactory {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    static {
        SERVICE_MAP.put(SIGN_UP_SERVICE, new SignUpService());
        SERVICE_MAP.put(LOG_IN_SERVICE, new LoginService());
        SERVICE_MAP.put(LOG_OUT_SERVICE, new LogoutService());
        SERVICE_MAP.put(BLOCK_USER_SERVICE, new BlockUserService());
        SERVICE_MAP.put(CHANGE_PASSWORD_SERVICE, new ChangePasswordService());
        SERVICE_MAP.put(GET_ALL_BOOKS_SERVICE, new GetAllBooksService());
        SERVICE_MAP.put(GET_ALL_USERS_SERVICE, new GetAllUsersService());
        SERVICE_MAP.put(GET_ALL_ORDERS_SERVICE, new GetAllOrdersService());
        SERVICE_MAP.put(GET_ALL_CATEGORIES_SERVICE, new GetAllCategoriesService());
        SERVICE_MAP.put(GET_ALL_AUTHORS_SERVICE, new GetAllAuthorsService());
        SERVICE_MAP.put(CHANGE_LANGUAGE_SERVICE, new ChangeLanguageService());
        SERVICE_MAP.put(ADD_NEW_CATEGORY_SERVICE, new AddNewCategoryService());
        SERVICE_MAP.put(EDIT_CATEGORY_SERVICE, new EditCategoryService());
        SERVICE_MAP.put(EDIT_BOOK_SERVICE, new EditBookService());
        SERVICE_MAP.put(DELETE_ENTITY_SERVICE, new DeleteEntityService());
        SERVICE_MAP.put(DELETE_ENTITY_ADMIN_SERVICE, new DeleteEntityAdmin());
        SERVICE_MAP.put(ADD_NEW_AUTHOR_SERVICE, new AddNewAuthorService());
        SERVICE_MAP.put(EDIT_AUTHOR_SERVICE, new EditAuthorService());
        SERVICE_MAP.put(EDIT_IMAGE_SERVICE, new EditImageService());
        SERVICE_MAP.put(WELCOME_SERVICE, new WelcomeService());
        SERVICE_MAP.put(ADD_TO_CART_SERVICE, new AddToCartService());
        SERVICE_MAP.put(ADD_BOOK_SERVICE, new AddNewBookService());
        SERVICE_MAP.put(ADD_TO_WISHLIST_SERVICE, new AddToWishListService());
        SERVICE_MAP.put(EDIT_PROFILE_SERVICE, new EditProfileService());
        SERVICE_MAP.put(ADD_CART_SERVICE, new AddCardService());
        SERVICE_MAP.put(ADD_ADDRESS_SERVICE, new AddAddressService());
        SERVICE_MAP.put(UPDATE_QUANTITY_SERVICE, new UpdateQuantityService());
        SERVICE_MAP.put(CREATE_ORDER_SERVICE, new CreateOrderService());
        SERVICE_MAP.put(UPDATE_STATUS_SERVICE, new UpdateStatusService());
        SERVICE_MAP.put(SEARCH_SERVICE, new SearchService());
        SERVICE_MAP.put(FILTER_BOOKS_SERVICE, new BookFilterService());
        SERVICE_MAP.put(CHECKOUT_SERVICE, new CheckoutService());
        SERVICE_MAP.put(GET_IMAGE_SERVICE, new GetImageService());
    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public Service getService(String serviceName) {
        Service service = null;
        try {
            service = SERVICE_MAP.get(serviceName);
        } catch (NullPointerException e) {
            LOGGER.info(e);
        }
        return service;
    }
}

