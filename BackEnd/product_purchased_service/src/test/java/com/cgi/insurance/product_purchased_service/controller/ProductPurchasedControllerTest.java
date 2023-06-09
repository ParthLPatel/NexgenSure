package com.cgi.insurance.product_purchased_service.controller;

import com.cgi.insurance.product_purchased_service.model.ProductPurchased;
import com.cgi.insurance.product_purchased_service.service.ProductPurchasedService;
import com.cgi.insurance.product_purchased_service.utils.LocalDateAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductPurchasedControllerTest {
    private MockMvc mockMvc;
    @Mock
    ProductPurchasedService ppService;
    @InjectMocks
    private ProductPurchasedController ppController;

    private ProductPurchased pp;
    private List<ProductPurchased> ppList;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ppController).build();
        pp = new ProductPurchased("ppId1","product1","comp1","clien1","bundle1",null,11.11,"toronto","","","");
        ppList = new ArrayList<>();
        ppList.add(pp);
    }

    @AfterEach
    public void tearDown() {
        pp = null;
        ppList=null;
    }

    @Test
    public void givenProductPurchasedToSaveThenShouldReturnSavedProductPurchased() throws Exception {
        //System.out.println(pp);
        when(ppService.addProductPurchased(any())).thenReturn(pp);
        mockMvc.perform(post("/api/v1/purchased/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pp)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).addProductPurchased(any());
    }

    @Test
    public void givenProductPurchasedToUpdateThenShouldReturnUpdatedProductPurchased() throws Exception {
        when(ppService.updateProductPurchased(any())).thenReturn(pp);
        mockMvc.perform(put("/api/v1/purchased/update/"+pp.getPpId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(pp)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenProductPurchasedIdToDeleteThenShouldNotReturnDeletedProductPurchased() throws Exception {
        mockMvc.perform(delete("/api/v1/purchased/delete/"+pp.getPpId()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenGetAllProductPurchasedsThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getAllProductPurchased()).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/viewAll"))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchased();
        verify(ppService, times(1)).getAllProductPurchased();

    }

    @Test
    public void givenGetAllProductPurchasedsByProductIdThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getAllProductPurchasedByProductId(pp.getProductId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/product/"+pp.getProductId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByProductId(pp.getProductId());
        verify(ppService, times(1)).getAllProductPurchasedByProductId(pp.getProductId());

    }

    @Test
    public void givenGetAllProductPurchasedsByClientIdThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getAllProductPurchasedByClientId(pp.getClientId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/client/"+pp.getClientId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByClientId(pp.getClientId());
        verify(ppService, times(1)).getAllProductPurchasedByClientId(pp.getClientId());

    }

    @Test
    public void givenGetAllPPsNotInBundlesByClientIdThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getAllProductPurchasedByClientIdNotInBundles(pp.getClientId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/notbundle/client/"+pp.getClientId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByClientIdNotInBundles(pp.getClientId());
        verify(ppService, times(1)).getAllProductPurchasedByClientIdNotInBundles(pp.getClientId());

    }

    @Test
    public void givenGetAllPPsByBundleIdThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getAllProductPurchasedByBundleId(pp.getBundleId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/bundle/"+pp.getBundleId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByBundleId(pp.getBundleId());
        verify(ppService, times(1)).getAllProductPurchasedByBundleId(pp.getBundleId());
    }

    @Test
    public void givenGetAllPPsByCompanayIdThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getAllProductPurchasedByCompanyId(pp.getCompanyId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/company/"+pp.getCompanyId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByCompanyId(pp.getCompanyId());
        verify(ppService, times(1)).getAllProductPurchasedByCompanyId(pp.getCompanyId());
    }

    @Test
    public void givenGetAllPPsByClientIdAndBundleIdThenShouldReturnListOfAllProductPurchaseds() throws Exception {
        when(ppService.getALlProductPurchasedByClientIdAndBundleId(pp.getClientId(),pp.getBundleId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/bundle/"+pp.getClientId()+"/"+pp.getBundleId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getALlProductPurchasedByClientIdAndBundleId(pp.getClientId(),pp.getBundleId());
        verify(ppService, times(1)).getALlProductPurchasedByClientIdAndBundleId(pp.getClientId(),pp.getBundleId());
    }

    @Test
    public void givenGetPPByPPIdThenShouldReturnProductPurchased() throws Exception {
        when(ppService.getProductPurchasedByPPId(pp.getPpId())).thenReturn(pp);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/id/"+pp.getPpId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getProductPurchasedByPPId(pp.getPpId());
        verify(ppService, times(1)).getProductPurchasedByPPId(pp.getPpId());
    }

    @Test
    public void givenGetPPByClientIdAndProductIdThenShouldReturnProductPurchased() throws Exception {
        when(ppService.getAllProductPurchasedByClientIdAndProductId(pp.getClientId(),pp.getProductId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/client/"+pp.getClientId()+"/product/"+pp.getProductId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByClientIdAndProductId(pp.getClientId(),pp.getProductId());
        verify(ppService, times(1)).getAllProductPurchasedByClientIdAndProductId(pp.getClientId(),pp.getProductId());
    }


    @Test
    public void givenGetPPByDateAndClientIdThenShouldReturnProductPurchased() throws Exception {
        when(ppService.getAllProductPurchasedByClientIdPurchaseDate(pp.getClientId(),pp.getPurchaseDate())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/client/"+pp.getClientId()+"/date/"+pp.getPurchaseDate()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByClientIdPurchaseDate(pp.getClientId(),pp.getPurchaseDate());
        verify(ppService, times(1)).getAllProductPurchasedByClientIdPurchaseDate(pp.getClientId(),pp.getPurchaseDate());
   
    }


    @Test
    public void givenGetPPByCompanyIdProductIdAndThenShouldReturnProductPurchased() throws Exception {
        when(ppService.getAllProductPurchasedByCompanyIdAndProductId(pp.getCompanyId(),pp.getProductId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/company/"+pp.getCompanyId()+"/product/"+pp.getProductId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByCompanyIdAndProductId(pp.getCompanyId(),pp.getProductId());
        verify(ppService, times(1)).getAllProductPurchasedByCompanyIdAndProductId(pp.getCompanyId(),pp.getProductId());
    }

    @Test
    public void givenGetPPByCompanyIdAndBundleIdThenShouldReturnProductPurchased() throws Exception {
        when(ppService.getAllProductPurchasedByCompanyIdAndBundleId(pp.getCompanyId(),pp.getBundleId())).thenReturn(ppList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchased/view/company/"+pp.getCompanyId()+"/bundle/"+pp.getBundleId()))
                .andDo(MockMvcResultHandlers.print());
        verify(ppService).getAllProductPurchasedByCompanyIdAndBundleId(pp.getCompanyId(),pp.getBundleId());
        verify(ppService, times(1)).getAllProductPurchasedByCompanyIdAndBundleId(pp.getCompanyId(),pp.getBundleId());
    }

    public static String asJsonString(final Object obj) {
        try {

            // return new ObjectMapper().writeValueAsString(obj);
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        return gson.toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
