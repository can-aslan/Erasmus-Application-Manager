import Lottie from "react-lottie-player";
import loadingLottie from '../../assets/screen_loading_lottie.json';

const ScreenLoadingLottie = () => {
    return (<Lottie
        style={{ width: 600, height: 600 }}
        loop
        animationData={loadingLottie}
        play />);
}

export default ScreenLoadingLottie;