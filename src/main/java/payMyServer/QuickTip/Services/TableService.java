package payMyServer.QuickTip.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payMyServer.QuickTip.Entity.Table;
import payMyServer.QuickTip.Respository.TableRepo;


import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    TableRepo tableRepository;

    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableRepository.findAll());
    }

    public ResponseEntity<Table> getTableById(String id) {
        Optional<Table> tableOpt = tableRepository.findById(id);
        if (tableOpt.isPresent()) {
            return ResponseEntity.ok(tableOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Table> addTable(Table table) {
        Table saved = tableRepository.save(table);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<Table> updateTable(String id, Table newTable) {
        Optional<Table> tableOpt = tableRepository.findById(id);
        if (tableOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Table table = tableOpt.get();
        if (newTable.getNumber() != 0) {
            table.setNumber(newTable.getNumber());
        }
        if (newTable.getQrCodeUrl() != null && !newTable.getQrCodeUrl().isEmpty()) {
            table.setQrCodeUrl(newTable.getQrCodeUrl());
        }
        Table updated = tableRepository.save(table);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<String> deleteTable(String id) {
        Optional<Table> tableOpt = tableRepository.findById(id);
        if (tableOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tableRepository.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
