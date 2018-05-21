package route;

import java.util.ArrayList;

public interface RouteDAO {
    int saveRoute(Route route);
    Route getRouteById(int id);
}
