import Lottie from "react-lottie-player";
import capybaraLottie from '../../assets/capybara_chilling_lottie.json';

const CapybaraLottie = () => {
    return (<Lottie
        style={{ width: 600, height: 600 }}
        loop
        animationData={capybaraLottie}
        play />);
}

export default CapybaraLottie;