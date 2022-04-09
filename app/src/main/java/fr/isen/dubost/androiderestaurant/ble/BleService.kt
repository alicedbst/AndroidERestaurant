package fr.isen.dubost.androiderestaurant.ble

import android.bluetooth.BluetoothGattCharacteristic
import android.icu.text.CaseMap
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class BleService (val name: String, characteristics: MutableList<BluetoothGattCharacteristic>) :
    ExpandableGroup<BluetoothGattCharacteristic>(name,characteristics){


}