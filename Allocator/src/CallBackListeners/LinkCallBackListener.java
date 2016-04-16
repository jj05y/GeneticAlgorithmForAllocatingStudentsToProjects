package CallBackListeners;

/**
 * Created by joe on 15/04/16.
 */
public interface LinkCallBackListener {

        public void setStatus(String status);
        public void updateProgressBar(int i);
        public void resetProgressBar();

}
