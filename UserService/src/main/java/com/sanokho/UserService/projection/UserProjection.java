package com.sanokho.UserService.projection;

import com.sanokho.CommonService.model.CardDetails;
import com.sanokho.CommonService.model.User;
import com.sanokho.CommonService.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query){

       //Ideally Get the details from the DB
        CardDetails cardDetails
                = CardDetails.builder()
                .name("Souleymane SANOKHO")
                .getValidUntilYear(2022)
                .validUntilMonth(01)
                .cardNumber("98051925")
                .ccv(111)
                .build();

        return User.builder()
                .userId(query.getUserId())
                .firstName("Souleymane")
                .lastName("SANOKHO")
                .cardDetails(cardDetails)
                .build();
    }
}
