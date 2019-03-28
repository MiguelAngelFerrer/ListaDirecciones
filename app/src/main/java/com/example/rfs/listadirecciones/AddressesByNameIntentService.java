package com.example.rfs.listadirecciones;


import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AddressesByNameIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS

    private static final String IDENTIFIER = "AddressesByNameIS";
    private ResultReceiver addressResultReceiver;

    //private static final String ACTION_FOO = "com.example.rfs.listadirecciones.action.FOO";
    //private static final String ACTION_BAZ = "com.example.rfs.listadirecciones.action.BAZ";

    // TODO: Rename parameters
    // private static final String EXTRA_PARAM1 = "com.example.rfs.listadirecciones.extra.PARAM1";
    //private static final String EXTRA_PARAM2 = "com.example.rfs.listadirecciones.extra.PARAM2";

    // public AddressesByNameIntentService() {super("AddressesByNameIntentService");    }

    public AddressesByNameIntentService() {
        super(IDENTIFIER);
    }


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    //public static void startActionFoo(Context context, String param1, String param2) {
    //    Intent intent = new Intent(context, AddressesByNameIntentService.class);
    //    intent.setAction(ACTION_FOO);
    //    intent.putExtra(EXTRA_PARAM1, param1);
    //    intent.putExtra(EXTRA_PARAM2, param2);
    //    context.startService(intent);
    //}

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    //public static void startActionBaz(Context context, String param1, String param2) {
    //    Intent intent = new Intent(context, AddressesByNameIntentService.class);
    //    intent.setAction(ACTION_BAZ);
    //    intent.putExtra(EXTRA_PARAM1, param1);
    //    intent.putExtra(EXTRA_PARAM2, param2);
    //    context.startService(intent);
    //}

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = "";
        addressResultReceiver = intent.getParcelableExtra("address_receiver");

        if (addressResultReceiver == null) {
            Log.e(IDENTIFIER,
                    "No receiver in intent");
            return;
        }

        String addressName = intent.getStringExtra("address_name");

        if (addressName == null) {
            msg = "No name found";
            sendResultsToReceiver(0, msg, null);
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(addressName, 5);
        } catch (Exception ioException) {
            Log.e("", "Error in getting addresses for the given name");
        }

        if (addresses == null || addresses.size()  == 0) {
            msg = "No address found for the address name";
            sendResultsToReceiver(1, msg, null);
        } else {
            Log.d(IDENTIFIER, "number of addresses received "+addresses.size());
            String[] addressList = new String[addresses.size()] ;
            int j =0;
            for(Address address : addresses){
                ArrayList<String> addressInfo = new ArrayList<>();
                for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressInfo.add(address.getAddressLine(i));
                }
                addressList[j] = TextUtils.join(System.getProperty("line.separator"),
                        addressInfo);
                Log.d(IDENTIFIER,addressList[j]);
                j++;
            }
            sendResultsToReceiver(2,"", addressList);
        }
    }
    private void sendResultsToReceiver(int resultCode, String message, String[] addressList) {
        Bundle bundle = new Bundle();
        bundle.putString("msg", message);
        bundle.putStringArray("addressList", addressList);
        addressResultReceiver.send(resultCode, bundle);
    }


    //protected void onHandleIntent(Intent intent) {
    //    if (intent != null) {
    //        final String action = intent.getAction();
    //        if (ACTION_FOO.equals(action)) {
    //            final String param1 = intent.getStringExtra(EXTRA_PARAM1);
    //            final String param2 = intent.getStringExtra(EXTRA_PARAM2);
    //            handleActionFoo(param1, param2);
    //       } else if (ACTION_BAZ.equals(action)) {
    //            final String param1 = intent.getStringExtra(EXTRA_PARAM1);
    //            final String param2 = intent.getStringExtra(EXTRA_PARAM2);
    //            handleActionBaz(param1, param2);
    //        }
    //    }
    //}

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    //private void handleActionFoo(String param1, String param2) {
    //    // TODO: Handle action Foo
    //    throw new UnsupportedOperationException("Not yet implemented");
    //}

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    //private void handleActionBaz(String param1, String param2) {
    //    // TODO: Handle action Baz
    //    throw new UnsupportedOperationException("Not yet implemented");
    //}
}
