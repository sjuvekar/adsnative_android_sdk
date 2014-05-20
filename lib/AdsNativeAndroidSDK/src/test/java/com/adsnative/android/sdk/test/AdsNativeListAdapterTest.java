package com.adsnative.android.sdk.test;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.adsnative.android.sdk.adapter.AdsNativeListAdapter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class AdsNativeListAdapterTest {

    private AdsNativeListAdapter adsNativeListAdapter;
    private Context context;

    @Before
    public void setup() {
        context = Robolectric.getShadowApplication().getApplicationContext();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("i"+i);
        }
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
        adsNativeListAdapter = new AdsNativeListAdapter(context, adapter, new int[]{3, 6, 13}, "Uw8JRh5gifh9sxZKZ-IRgVC0WNcgOGWxSyEFjObs");
    }

    @Test
    public void testGetCount(){
        Assert.assertEquals(10, adsNativeListAdapter.getCount());
    }

    @Test
    public void testGetViewTypeCount(){
        Assert.assertEquals(2, adsNativeListAdapter.getViewTypeCount());
    }

    @Test
    public void testGetItemViewType(){
        Assert.assertEquals(1, adsNativeListAdapter.getItemViewType(5));
    }

    @Test
    public void testGetItem(){
        Assert.assertNotNull(adsNativeListAdapter.getItem(5));
        Assert.assertEquals("i5", adsNativeListAdapter.getItem(5));
    }

    @Test
    public void testGetItemId(){
        Assert.assertNotNull(adsNativeListAdapter.getItemId(5));
        Assert.assertEquals(5, adsNativeListAdapter.getItemId(5));
    }

    @Test
    public void testGetView(){
        Assert.assertNotNull(adsNativeListAdapter.getView(5, null, null));
    }

}
