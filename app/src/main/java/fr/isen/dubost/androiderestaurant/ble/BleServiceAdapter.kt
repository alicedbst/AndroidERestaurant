package fr.isen.dubost.androiderestaurant.ble


import android.bluetooth.BluetoothGattCharacteristic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.isen.dubost.androiderestaurant.R

class BleServiceAdapter(
    bleService: List<BleService>,
    //bleService: MutableList<BleService>,
    private val readCharacteristicCallback: (BluetoothGattCharacteristic) -> Unit,
    private val writeCharacteristicCallback: (BluetoothGattCharacteristic) -> Unit
    ):
    ExpandableRecyclerViewAdapter<BleServiceAdapter.ServiceViewHolder, BleServiceAdapter.CharacteristicViewHolder>(
        bleService

    ){

    class ServiceViewHolder(itemView: View) : GroupViewHolder(itemView){
        var serviceName: TextView = itemView.findViewById(R.id.serviceName)
        var serviceUUID: TextView = itemView.findViewById(R.id.serviceUUID)
        var serviceArrow: TextView = itemView.findViewById(R.id.serviceName)

    }
    class CharacteristicViewHolder(itemView: View): ChildViewHolder(itemView){
        var characteristicName: TextView = itemView.findViewById(R.id.characteristicName)
        var characteristicUUID: TextView = itemView.findViewById(R.id.characteristicUUID)
        var characteristicRead: Button = itemView.findViewById(R.id.characteristicRead)
        var characteristicWrite: Button = itemView.findViewById(R.id.characteristicWrite)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ble_service, parent, false)
        return ServiceViewHolder(itemView)

    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacteristicViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ble_characteristic, parent, false)
        return CharacteristicViewHolder(itemView)
    }

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val characteristic = group.items[childIndex] as BluetoothGattCharacteristic
        val title =
            BLEUUIDAttributes.getBLEAttributeFromUUID(characteristic.uuid.toString()).title

        holder.characteristicName.text = characteristic.uuid.toString()
        val uuidMessage = "UUID : ${characteristic.uuid}"
        holder.characteristicUUID.text = uuidMessage

        holder.characteristicName.text = title
        val properties = arrayListOf<String>()

        addPropertyFromCharacteristic(
            characteristic,
            properties,
            "Lecture",
            BluetoothGattCharacteristic.PROPERTY_READ,
            holder.characteristicRead,
            readCharacteristicCallback
        )

        addPropertyFromCharacteristic(
            characteristic,
            properties,
            "Ecrire",
            (BluetoothGattCharacteristic.PROPERTY_WRITE or BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE),
            holder.characteristicWrite,
            writeCharacteristicCallback
        )
    }

    private fun addPropertyFromCharacteristic(
        characteristic: BluetoothGattCharacteristic,
        properties: ArrayList<String>,
        propertyName: String,
        propertyType: Int,
        propertyAction: Button,
        propertyCallBack: (BluetoothGattCharacteristic) -> Unit
    ) {
        if ((characteristic.properties and propertyType) != 0) {
            properties.add(propertyName)
            propertyAction.isEnabled = true
            propertyAction.alpha = 1f
            propertyAction.setOnClickListener {
                propertyCallBack.invoke(characteristic)
            }
        }

    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        val title =
            BLEUUIDAttributes.getBLEAttributeFromUUID(group.title).title
        holder.serviceName.text = title
        holder.serviceUUID.text = group.title
        holder.serviceArrow
    }
}
