package com.sanokho.PaymentService.command.api.aggregate;

import com.sanokho.CommonService.commands.CancelPaymentCommand;
import com.sanokho.CommonService.commands.ValidatePaymentCommand;
import com.sanokho.CommonService.events.PaymentCancelEvent;
import com.sanokho.CommonService.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        //Valida the Payment Details
        //Publish the Payment Processed event
        log.info("Executing ValidatePaymentCommand for" +
                "Order Id: {} and Payment Id: {}",
                validatePaymentCommand.getOrderId(),
                validatePaymentCommand.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent
                = new PaymentProcessedEvent(
                        validatePaymentCommand.getPaymentId(), validatePaymentCommand.getOrderId()
        );

        AggregateLifecycle.apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event){

        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();

    }

    @CommandHandler
    public void handle(CancelPaymentCommand cancelPaymentCommand){
        PaymentCancelEvent paymentCancelEvent
                =new PaymentCancelEvent();

        BeanUtils.copyProperties(cancelPaymentCommand, paymentCancelEvent);

        AggregateLifecycle.apply(paymentCancelEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCancelEvent event){
        this.paymentStatus = event.getPaymentStatus();
    }
}
