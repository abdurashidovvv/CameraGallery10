package uz.abdurashidov.cameragallery10.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.abdurashidov.cameragallery10.databinding.RvItemBinding
import uz.abdurashidov.cameragallery10.models.User

class RvAdapter(val list: List<User>) : RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(var itemRv: RvItemBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(user: User) {
            itemRv.image.setImageURI(Uri.parse(user.image))
            itemRv.name.text = user.name
            itemRv.surname.text = user.surname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick {
        fun itemClick(text: String)
    }

}