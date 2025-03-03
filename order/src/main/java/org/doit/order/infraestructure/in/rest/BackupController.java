package org.doit.order.infraestructure.in.rest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doit.order.application.usecase.DeleteAllOrdersUseCase;
import org.doit.order.infraestructure.in.stream.OrderEventBackupConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/backup")
@RequiredArgsConstructor
public class BackupController {

    private final DeleteAllOrdersUseCase deleteAllOrdersUseCase;
    private final OrderEventBackupConsumer orderEventBackupConsumer;

    @PostMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAll() {
        log.info("🎯 Entering deleteAll");
        deleteAllOrdersUseCase.deleteAll();
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @PostMapping("/recreateAllEvents")
    public ResponseEntity<Boolean> recreateAllEvents() {
        log.info("🎯 Entering recreateAllEvents");
        var isSuccess = orderEventBackupConsumer.runBackup();
            return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
