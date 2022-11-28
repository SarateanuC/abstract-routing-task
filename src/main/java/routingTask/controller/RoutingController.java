package routingTask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import routingTask.entity.DbConnection;
import routingTask.requestDto.DboConnectionAddRequest;
import routingTask.service.DataSourceService;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class RoutingController {
    private final DataSourceService dataSourceService;

    @PutMapping("/add-connection")
    public void insertConnection(@RequestBody DboConnectionAddRequest dbConnection){
        dataSourceService.insertDataSource(dbConnection);
    }

    @DeleteMapping("/remove-connection")
    public void removeConnection(@RequestParam("id") String connection_id){
        dataSourceService.removeDataSource(connection_id);
    }

    @GetMapping("/all")
    public List<DbConnection> allDataSources(){
        return dataSourceService.getAllDataSources();
    }
}
