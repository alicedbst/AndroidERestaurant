package fr.isen.dubost.androiderestaurant.ble

import android.annotation.SuppressLint
import android.bluetooth.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.dubost.androiderestaurant.R
import fr.isen.dubost.androiderestaurant.databinding.ActivityBleDeviceBinding
import fr.isen.dubost.androiderestaurant.databinding.ActivityDetailBinding

@SuppressLint("MissingPermission")
class BleDeviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBleDeviceBinding
    private var bluetoothGatt : BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBleDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice?>(BleScanActivity.DEVICE_KEY)
        binding.deviceName.text = device?.name ?: "Nom inconnu"
        binding.deviceStatue.text = getString(R.string.ble_device_disconnected)

        connectToDevice(device)
    }


    private fun connectToDevice(device: BluetoothDevice?) {
        bluetoothGatt = device?.connectGatt(this, true, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                onConnectionStateChange(gatt, newState)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                val bleService= gatt?.services?.map { BleService(it.uuid.toString(), it.characteristics) } ?: arrayListOf()
                val adapter= BleServiceAdapter(bleService,{characteristic -> gatt?.readCharacteristic(characteristic)},{characteristic -> writeIntoCharacteristic(gatt, characteristic)})
                runOnUiThread {
                    binding.serviceList.layoutManager = LinearLayoutManager(this@BleDeviceActivity)
                    binding.serviceList.adapter = adapter
                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
            }
        })
        bluetoothGatt?.connect()
    }
    private fun writeIntoCharacteristic(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic
    ) {
        runOnUiThread {
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(16, 0, 16, 0)
            input.layoutParams = params

            AlertDialog.Builder(this@BleDeviceActivity)
                .setTitle(R.string.ble_device_write_characteristic_title)
                .setView(input)
                .setPositiveButton(R.string.ble_device_write_characteristic_confirm) { _, _ ->
                    characteristic.value = input.text.toString().toByteArray()
                    gatt?.writeCharacteristic(characteristic)
                    gatt?.readCharacteristic(characteristic)
                }
                .setNegativeButton(R.string.ble_device_write_characteristic_cancel) { dialog, _ -> dialog.cancel() }
                .create()
                .show()
        }
    }

    private fun onConnectionStateChange (gatt: BluetoothGatt?, newState: Int){
        val state = if(newState == BluetoothProfile.STATE_CONNECTED) {
            getString(R.string.ble_device_connnected)
        }else{
            getString(R.string.ble_device_disconnected)
        }
        runOnUiThread {
            binding.deviceStatue.text = state
        }
    }

    override fun onStop() {
        super.onStop()
        closeBluetoothGatt()
    }

    private fun closeBluetoothGatt() {
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}