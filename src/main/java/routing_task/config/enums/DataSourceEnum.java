package routing_task.config.enums;

import java.util.List;

public enum DataSourceEnum {
    DATASOURCE_ONE,
    DATASOURCE_TWO,
    DATASOURCE_THREE;

    private static final List<DataSourceEnum> listOfDb = List.of(DATASOURCE_ONE,DATASOURCE_TWO,DATASOURCE_THREE);

    public static List<DataSourceEnum> getListOfDb(){
        return listOfDb;
    }


}
