package net.metabaron.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {

    protected BluetoothAdapter myBluetoothAdapter;

    {
        myBluetoothAdapter = null;
    }

    protected int REQUEST_ENABLE_BT;

    {
        REQUEST_ENABLE_BT = 1;
    }

    private ArrayAdapter<String> mArrayAdapter;
    private ListView bluetoothListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothListView = (ListView) findViewById(R.id.listView);

        //Bluethooth
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (myBluetoothAdapter == null) {
            System.out.println("No Bluetooth");
        } else {
            System.out.println("Bluetooth");
            if (!myBluetoothAdapter.isEnabled()) {
                System.out.println("No Bluetooth Adapter");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                System.out.println("Bluetooth Adapter");
                dealBluetooth();
            }
        }
    }

    private void dealBluetooth() {
        System.out.println("Bluetooth ON = Good to go!");
        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                System.out.println(device.getAddress());
                mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            if (mArrayAdapter.getCount() != 0) {
                bluetoothListView.setAdapter(mArrayAdapter);
            } else {
                System.out.println("No devices discovered");
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                System.out.println("User enabled Bluetooth");
                dealBluetooth();
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("User do not enabled Bluetooth");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}