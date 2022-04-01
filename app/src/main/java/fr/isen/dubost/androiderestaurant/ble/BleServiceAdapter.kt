package fr.isen.dubost.androiderestaurant.ble


import android.bluetooth.BluetoothGattCharacteristic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.isen.dubost.androiderestaurant.R

class BleServiceAdapter(bleService: List<BleService>) :
    ExpandableRecyclerViewAdapter<BleServiceAdapter.ServiceViewHolder, BleServiceAdapter.CharacteristicViewHolder>(bleService) {

    class ServiceViewHolder(itemView: View) : GroupViewHolder(itemView){
        var serviceName: TextView = itemView.findViewById(R.id.serviceName)
        var serviceUUID: TextView = itemView.findViewById(R.id.serviceUUID)
        var serviceArrow: TextView = itemView.findViewById(R.id.serviceName)

    }
    class CharacteristicViewHolder(itemView: View): ChildViewHolder(itemView){
        var characteristicName: TextView = itemView.findViewById(R.id.characteristicName)
        var characteristicUUID: TextView = itemView.findViewById(R.id.characteristicUUID)
        var characteristicRead: TextView = itemView.findViewById(R.id.characteristicRead)
        var characteristicWrite: TextView = itemView.findViewById(R.id.characteristicWrite)
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
        holder.characteristicName.text = characteristic.uuid.toString()
    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        holder.serviceArrow
    }
}
