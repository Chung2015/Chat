package cc.heicoco.chat.controller.adapter;

import java.util.List;

import cc.heicoco.chat.R;
import cc.heicoco.chat.model.pojo.Friend;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendAdapter extends ArrayAdapter<Friend> {

	private int resourceId ;
	public FriendAdapter(Context context, int resource, List<Friend> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Friend fri = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.ip = (TextView)view.findViewById(R.id.text_friend_ip);
			viewHolder.name = (TextView)view.findViewById(R.id.text_firend_name);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.ip.setText(fri.getIp());
		viewHolder.name.setText(fri.getName());
		return view;
	}

	private class ViewHolder{
		TextView name;
		TextView ip;
	}
}
