package befaster.solutions;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutTest {

    @Test
    public void compute_sum() {
        assertThat(Sum.sum(1, 1), equalTo(2));
    }
    
    @Test
    public void compute_null_checkout() {
        assertThat(Checkout.checkout(null), equalTo(-1));
    }
    
    @Test
    public void compute_empty_checkout() {
        assertThat(Checkout.checkout(""), equalTo(0));
    }
    
    @Test
    public void compute_single_item_A_checkout() {
        assertThat(Checkout.checkout("A"), equalTo(50));
    }
    
    @Test
    public void compute_single_item_B_checkout() {
        assertThat(Checkout.checkout("B"), equalTo(30));
    }
    
    @Test
    public void compute_multipleItems_checkout() {
        assertThat(Checkout.checkout("ABCAD"), equalTo(165));
    }
    
    @Test
    public void compute_discount_checkout() {
        assertThat(Checkout.checkout("ABCADA"), equalTo(195));
    }
    

    @Test
    public void compute_discountB_checkout() {
        assertThat(Checkout.checkout("ABBCADA"), equalTo(210));
    }
    
    @Test
    public void compute_invalidSku_checkout() {
        assertThat(Checkout.checkout("-"), equalTo(-1));
    }
}