package co.edu.uptc.presenter;

public interface ContractPlay {

    public interface Model {

    }

    public interface View {
        public void setPresenter(Presenter presenter);

        public void run();
    }

    public interface Presenter {

        public void setView(View view);

        public void run();

    }
}
