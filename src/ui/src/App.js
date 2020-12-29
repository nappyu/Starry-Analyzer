import './App.css';
import React, {Component} from "react";
import background from './assets/background.jpg';
import data from './output';
import StarContainer from './components/Star/StarContainer';
import Legend from "./components/misc/Legend";
import bgm from './assets/interstellar-bgm.mp3';
import LandingPage from "./components/LandingPage/LandingPage";

class App extends Component{

    constructor(props) {
        super(props);
        this.state = {
            data: null,
            isModalOpen: false,
            currentClassData: null,
            currentClassName: null,
            landingPage: true
        }
    }

    componentWillMount() {
        this.setState({
            data: data,
            currentClassName: data[0].className,
            currentClassData: data[0]
        })
    }

    switchModal = (className) => {
        let temp = this.state.currentClassData;
        data.forEach(c => {
            if (c.className === className) {
                temp = c;
            }
        })
        this.setState({
            isModalOpen: false,
            currentClassData: temp,
            currentClassName: temp.className
        })
    };

    closeLandingPage = () => {
        this.setState({
            landingPage: false
        })
    };

    render() {
        const {data, isModalOpen, landingPage, currentClassData, currentClassName} = this.state;
        return (
            <div>
                {landingPage?
                    <LandingPage enter={this.closeLandingPage}/>
                    :
                    <div className="App">

                        <div className="root">
                            <span className="sub-title">
                                <p>Currently Viewing Class: </p>
                                <div className="change-class-button" onClick={() => this.setState({ isModalOpen: true })}>{currentClassName}</div>
                            </span>

                            <StarContainer data={currentClassData}/>
                            <div className="modal" style={{display: isModalOpen? 'block' : 'none'}}>
                                <div className="modal-content">
                                    <span className="close" onClick={() => this.setState({ isModalOpen: false })}>&times;</span>
                                    {
                                        data.map(c => {
                                            return <div className="class-option" onClick={() => this.switchModal(c.className)}>{c.className}</div>
                                        })
                                    }
                                </div>
                            </div>
                        </div>
                        <Legend/>
                        <img src={background} className="background" alt="background"/>
                    </div>
                }
                <audio ref="bgm" autoPlay={true}>
                    <source type="audio/mp3" src={bgm}/>
                </audio>
            </div>
        );
    }

}

export default App;
