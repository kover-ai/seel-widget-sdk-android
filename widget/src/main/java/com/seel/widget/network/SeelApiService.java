package com.seel.widget.network;

import com.seel.widget.models.EventsRequest;
import com.seel.widget.models.EventsResponse;
import com.seel.widget.models.QuotesRequest;
import com.seel.widget.models.QuotesResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Seel API Service Interface
 */
public interface SeelApiService {
    
    /**
     * Get quote
     */
    @POST("v1/ecommerce/quotes")
    Call<QuotesResponse> getQuotes(@Body QuotesRequest request);
    
    /**
     * Create event
     */
    @POST("v1/ecommerce/events")
    Call<EventsResponse> createEvents(@Body EventsRequest request);
}
