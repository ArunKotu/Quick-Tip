package payMyServer.QuickTip.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payMyServer.QuickTip.Entity.Table;
import payMyServer.QuickTip.Services.TableService;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    TableService tableService;

    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        return tableService.getAllTables();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable String id) {
        return tableService.getTableById(id);
    }
    @PostMapping("/admin")
    public ResponseEntity<Table> addTable(@RequestBody Table table) {
        return tableService.addTable(table);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Table> updateTable(@PathVariable String id, @RequestBody Table newTable) {
        return tableService.updateTable(id, newTable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable String id) {
        return tableService.deleteTable(id);
    }
}
