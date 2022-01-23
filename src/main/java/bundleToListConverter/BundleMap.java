package bundleToListConverter;

import java.util.*;

public class BundleMap {

    private static BundleMap instance;

    public List<String> convertBundleToList(ResourceBundle resource) {
        List <String> list = new ArrayList<>();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            list.add(key);
        }
        return list;
    }

    public static BundleMap getInstance() {
        return instance;
    }
}
