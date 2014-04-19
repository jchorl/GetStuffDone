package com.joshchorlton.getstuffdone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joshchorlton.getstuffdone.data.DatabaseHandler;
import com.joshchorlton.getstuffdone.data.Todo;

/**
 * A fragment representing a single Todo detail screen. This fragment is either
 * contained in a {@link TodoListActivity} in two-pane mode (on tablets) or a
 * {@link TodoDetailActivity} on handsets.
 */
public class TodoDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The Todo this fragment is presenting.
	 */
	private Todo mItem;
	
	private TextView textContent;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public TodoDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DatabaseHandler.getInstance(getActivity()).getTodo(getArguments().getLong(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_todo_detail,
				container, false);

		if (mItem != null) {
			textContent = ((TextView) rootView.findViewById(R.id.textContent));
            textContent.setText(mItem.content);
		}

		return rootView;
	}
	
	@Override
    public void onPause() {
        super.onPause();
        updateTodoFromUI();
    }

    private void updateTodoFromUI() {
        if (mItem != null && DatabaseHandler.getInstance(getActivity()).getTodo(mItem.id)!=null) {
            mItem.content = textContent.getText().toString();
            DatabaseHandler.getInstance(getActivity()).putTodo(mItem);
        }
    }

}