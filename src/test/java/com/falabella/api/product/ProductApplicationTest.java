package com.falabella.api.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductApplicationTest {

    @Test
    public void contextLoaded() {
        Mockito.mock(ProductApplication.class);
        ProductApplication productApplication = new ProductApplication();
    }

    /*
    @Test
    public void main() {
        ProductApplication.main(new String[] {});
    }
    */
}