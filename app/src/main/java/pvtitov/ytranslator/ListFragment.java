package pvtitov.ytranslator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;


/**
 * Created by Павел on 06.06.2017.
 */

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

    private onSelectWordListener callbackToActivity;

    public interface onSelectWordListener{
        void onSelectingWord(Word word);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbackToActivity = (onSelectWordListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onSelectWordListener interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.list_fragment);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        recyclerView.setLayoutManager(flexboxLayoutManager);

        updateUI();

        return view;
    }

    private void updateUI() {
        List<Word> words = WordLab.getSingleInstance(getActivity()).getListOfWords();
        if (listAdapter == null) {
            listAdapter = new ListAdapter(words);
            recyclerView.setAdapter(listAdapter);
        } else {
            listAdapter.setWords(words);
            listAdapter.notifyDataSetChanged();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Word word;
        private TextView wordTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            wordTextView = itemView.findViewById(R.id.list_item_text_word);
        }

        public void bindDataToItem(Word w){
            word = w;
            wordTextView.setText(word.getWord());
        }

        @Override
        public void onClick(View v) {
            callbackToActivity.onSelectingWord(word);
            new SubstituteTask(wordTextView, word).execute();
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ItemHolder>{

        private List<Word> list;

        public ListAdapter (List<Word> list){
            this.list = list;
        }


        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Word item = list.get(position);
            holder.bindDataToItem(item);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setWords(List<Word> words) {
            this.list = words;
        }
    }

    private class SubstituteTask extends AsyncTask<Void, Void, Void>{
        private TextView textView;
        private Word word;

        private SubstituteTask(TextView textView, Word word){
            this.textView = textView;
            this.word = word;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText(word.getTranslation());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(word.getWord());
            updateUI();
        }
    }
}
