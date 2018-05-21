package route;

import java.util.ArrayList;

public interface RouteStepDAO {
    boolean saveRouteSteps(ArrayList<RouteStep> steps, int routeId);
    ArrayList<RouteStep> getRouteSteps(int routeId);
}
