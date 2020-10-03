package org.maktab.photogallery.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab.photogallery.R;
import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.repository.PhotoRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoGalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoGalleryFragment extends Fragment {

    public static final int SPAN_COUNT = 3;
    public static final String TAG = "PGF";
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;

    private PhotoRepository mRepository;

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }

    public static PhotoGalleryFragment newInstance() {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = PhotoRepository.getInstance();

        FetchItemTasks fetchItemTasks = new FetchItemTasks();
        fetchItemTasks.execute(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        findViews(view);
        initViews();
//        setupAdapter(null);

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.photo_gallery_recyclerview);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter(List<GalleryItem> items) {
        if(mAdapter==null) {
            mAdapter = new PhotoAdapter(items);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private GalleryItem mItem;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = (TextView) itemView;
        }

        public void bindPhoto(GalleryItem item) {
            mItem = item;
            mTextViewTitle.setText(mItem.getCaption());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mItems;

        public List<GalleryItem> getItems() {
            return mItems;
        }

        public void setItems(List<GalleryItem> items) {
            mItems = items;
        }

        public PhotoAdapter(List<GalleryItem> items) {
            mItems = items;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(getContext());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            holder.bindPhoto(mItems.get(position));
            if(position==mItems.size()-1){
                int currentPage=mRepository.getCurrentPage();
                int pages=mRepository.getPages();
                if(currentPage<pages){
                    mRepository.setCurrentPage(++currentPage);
                    FetchItemTasks fetchItemTasks = new FetchItemTasks();
                    fetchItemTasks.execute(currentPage);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class FetchItemTasks extends AsyncTask<Integer, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Integer... params) {
            List<GalleryItem> items = mRepository.getItems(params[0]);

            return items;
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            setupAdapter(items);
        }
    }
}