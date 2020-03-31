package com.thestoryofus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class BookOptionsDialogFragment extends DialogFragment {
    public interface BookOptionsDialogListener {
        public void markBookAsRead(int bookID);
        public void removeBook(int bookID);
    }

    private static final String TAG = "BookOptionsDialogFragment";

    private static final String ARG_KEY_BOOK_ID = "argBookID";

    private static final int MARK_BOOK_AS_READ = 0;
    private static final int DELETE = 1;

    private BookOptionsDialogListener listener;

    /*
        Creates new BookOptionsDialog providing a viewID argument.
        Input: view ID
        Output: BookOptionsDialog object
     */
    public static BookOptionsDialogFragment newInstance(int bookID) {
        BookOptionsDialogFragment dialog = new BookOptionsDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_KEY_BOOK_ID, bookID);
        dialog.setArguments(args);

        return dialog;
    }

    /*
        Initializes (builds) dialog attributes - title, content (list of options) and sets
        listeners.
        Input: Bundle object
        Output: Initialized dialog object
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int bookID = getArguments().getInt(ARG_KEY_BOOK_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setCustomTitle(inflater.inflate(R.layout.dialog_book_options, null));
        builder.setItems(R.array.book_dialog_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case MARK_BOOK_AS_READ:
                                BookOptionsDialogFragment.this.listener.markBookAsRead(bookID);
                                break;
                            case DELETE:
                                BookOptionsDialogFragment.this.listener.removeBook(bookID);
                                break;
                        }
                    }
                });

        return builder.create();
    }

    /*
        Initializes dialog's listener, which should be the context, so that the dialog
        will be able to pass clicks back to the context. Handling clicks in the dialog is the
        context's responsibility.
        Throws exception in case the context didn't implement the interface
        BookOptionsDialogListener.

        Input: The context
        Output: None
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (BookOptionsDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement BookOptionsDialogListener");
        }
    }
}
