package com.nathy.app.command.api.aggregate;

//@Aggregate
public class BidAggregate {


//    @AggregateIdentifier
//    private String bidId;
//	private BigDecimal amount;
//	private String name;
//	private String email;
//	private String mobile;
//	private String productId;;
//
//    @CommandHandler
//    public BidAggregate(CreateBidCommand createBidCommand) {
//        //You can perform all the validations
//        BidCreatedEvent bidCreatedEvent =
//                new BidCreatedEvent();
//
//        BeanUtils.copyProperties(createBidCommand,bidCreatedEvent);
//
//        AggregateLifecycle.apply(bidCreatedEvent);
//    }
//
//    public BidAggregate() {
//    }
//
//    @EventSourcingHandler
//    public void on(BidCreatedEvent bidCreatedEvent) {
//        this.amount = bidCreatedEvent.getAmount();
//        this.bidId = bidCreatedEvent.getBidId();
//        this.email = bidCreatedEvent.getEmail();
//        this.name = bidCreatedEvent.getName();
//        this.mobile = bidCreatedEvent.getMobile();
//        this.productId = bidCreatedEvent.getProductId();
//    }
}