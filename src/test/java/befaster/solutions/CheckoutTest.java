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
    
    @Test
    public void compute_5As_discount_checkout() {
        assertThat(Checkout.checkout("AAAAA"), equalTo(200));
    }
    
    @Test
    public void compute_freeB_if_2Es_discount_checkout() {
        assertThat(Checkout.checkout("EBE"), equalTo(80));
    }
    
    @Test
    public void compute_2Fs_no_discount_checkout() {
        assertThat(Checkout.checkout("FF"), equalTo(20));
    }
    
    @Test
    public void compute_3Fs_means_1_free_checkout() {
        assertThat(Checkout.checkout("FFF"), equalTo(20));
    }
    
    @Test
    public void compute_5Fs_means_1_free_checkout() {
        assertThat(Checkout.checkout("FFFFF"), equalTo(40));
    }
    
    @Test
    public void compute_6Fs_means_2_free_checkout() {
        assertThat(Checkout.checkout("FFFFFF"), equalTo(40));
    }
    
    @Test
    public void compute_5Ps_for_200_checkout() {
        assertThat(Checkout.checkout("PPPPP"), equalTo(200));
    }
    
    @Test
    public void compute_3Us_for_120_checkout() {
        assertThat(Checkout.checkout("UUU"), equalTo(120));
    }
    
    @Test
    public void compute_groupDiscount_checkout() {
        assertThat(Checkout.checkout("XYZ"), equalTo(45));
    }
    
    @Test
    public void compute_SSSZ_checkout() {
        assertThat(Checkout.checkout("SSSZ"), equalTo(65));
    }
    
    @Test
    public void compute_ZZZS_checkout() {
        assertThat(Checkout.checkout("ZZZS"), equalTo(65));
    }
    
    @Test
    public void compute_STXS_checkout() {
        assertThat(Checkout.checkout("STXS"), equalTo(62));
    }
}