package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class ServiceFactory {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
        private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
        private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();
        static {
            SERVICE_MAP.put("sign_up", new SignUpService());
            SERVICE_MAP.put("log_in", new LoginService());
            SERVICE_MAP.put("log_out", new LogoutService());
            SERVICE_MAP.put("block_user", new BlockUserService());
            SERVICE_MAP.put("change_password", new ChangePasswordService());
            SERVICE_MAP.put("add_to_cart", new AddToCartService());
            SERVICE_MAP.put("get_all_books", new GetAllBooksService());
            SERVICE_MAP.put("get_all_users", new GetAllUsersService());
            SERVICE_MAP.put("get_all_orders", new GetAllOrdersService());
            SERVICE_MAP.put("get_all_messages", new GetAllMessagesService());
            SERVICE_MAP.put("get_all_categories", new GetAllCategoriesService());
            SERVICE_MAP.put("get_all_authors", new GetAllAuthorsService());
            SERVICE_MAP.put("change_language", new ChangeLanguageService());
            SERVICE_MAP.put("add_new_category", new AddNewCategoryService());
            SERVICE_MAP.put("edit_category", new EditCategoryService());
            SERVICE_MAP.put("edit_book", new EditBookService());
            SERVICE_MAP.put("delete_entity", new DeleteEntityService());
            SERVICE_MAP.put("add_new_author", new AddNewAuthorService());
            SERVICE_MAP.put("edit_author", new EditAuthorService());
            SERVICE_MAP.put("edit_image", new EditImageService());
            SERVICE_MAP.put("welcome", new WelcomeService());
            SERVICE_MAP.put("add_to_cart", new AddToCartService());
            SERVICE_MAP.put("add_book", new AddNewBookService());
            SERVICE_MAP.put("add_to_wl", new AddToWishListService());
            SERVICE_MAP.put("edit_profile", new EditProfileService());
            SERVICE_MAP.put("add_card", new AddCardService());
            SERVICE_MAP.put("add_address", new AddAddressService());
            SERVICE_MAP.put("update_quantity", new UpdateQuantityService());
            SERVICE_MAP.put("create_order", new CreateOrderService());
            SERVICE_MAP.put("update_status", new UpdateStatusService());
        }

        public static ServiceFactory getInstance() {
            return SERVICE_FACTORY;
        }

        public Service getService(String serviceName) {
            Service service = null;
            try {
                service = SERVICE_MAP.get(serviceName);
            }
            catch (NullPointerException e) {
                LOGGER.info(e);
            }
            return service;
        }
    }

