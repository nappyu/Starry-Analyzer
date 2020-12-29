import React, {Component} from "react";
import './Star.css';
import Xarrow from "react-xarrows";

class Star extends Component{

    constructor(props) {
        super(props);
        this.state = {
            x: null,
            y: null,
            isPopOverOpen: false
        }
    }

    componentDidMount() {
        document.addEventListener('mousedown', this.handleClickOutside);

    }

    componentWillUnmount() {
        document.removeEventListener('mousedown', this.handleClickOutside);
    }

    /**
     * Alert if clicked on outside of element
     */
    handleClickOutside = (event) => {
        if (event.target.outerText.indexOf(this.props.name) === -1) {
            this.setState({
                isPopOverOpen: false
            })
        }
    };

    getRandomPosition = () => {
        const xBoundary = window.innerWidth - 300;
        const yBoundary = window.innerHeight - 300;

        let randomX = Math.floor(Math.random() * window.innerWidth);
        let randomY = Math.floor(Math.random() * window.innerHeight);

        while (randomX < 0 || randomX > xBoundary || randomY < 100 || randomY > yBoundary) {
            randomX = Math.floor(Math.random() * window.innerWidth);
            randomY = Math.floor(Math.random() * window.innerHeight);
        }

        this.setState({
            x: randomX,
            y: randomY
        })
    };

    render() {
        const {name, status, messages, dependencies, methods} = this.props;
        const {x, y, isPopOverOpen} = this.state;


        if (this.state.x === null || this.state.y === null) this.getRandomPosition();

        let id = '';
        name.split(' ').forEach(n => {
            if (n.indexOf('(') !== -1)
                id = n.split('(')[0];
        });

        return (
            <div className="star" style={{top: y+'px', left: x+'px'}} id={id}>
                <div>
                    <div className="method-name" onClick={() => this.setState({isPopOverOpen: true})}
                         style={{animation: status === 'OK'? 'glow-green 1s ease-in-out infinite alternate' :
                                 status==='WARNING'? 'glow-yellow 1s ease-in-out infinite alternate' : 'glow-red 1s ease-in-out infinite alternate'}}>
                        {name}
                    </div>
                    {isPopOverOpen?
                        <div className="popover-content" style={{backgroundImage: status === 'HIGH_RISK'?
                                'linear-gradient( 109.1deg,  rgba(181,73,91,1) 7.1%, rgba(225,107,140,1) 86.4% )':
                                status === 'WARNING'?
                                    'radial-gradient( circle farthest-corner at 3.1% 8.2%,  rgba(248,250,107,1) 0%, rgba(238,148,148,1) 98.2% )':
                                    'linear-gradient( 112.9deg,  rgba(112,255,151,1) 6.2%, rgba(70,195,255,1) 99.7% )'}}>
                            Method Name: {name}<br/>
                            Status: {status}<br/>
                            <br/><br/>
                            Issues: <br/><br/>
                            {messages.length === 0? 'None' : messages.map(msg => msg.includes('Critical')?
                                <div style={{fontWeight: 'bold'}}>{msg}</div> : <div>{msg}</div>)}
                            <br/><br/><br/>
                            Method dependencies: <br/><br/>
                            {dependencies.length === 0? 'None' : dependencies.map(d => <div>{d === id? '(Recursive to self) '+d : d}</div>)}
                        </div> : <span/>
                    }

                </div>
                {dependencies.length === 0? '' : dependencies.map(d => {
                    return d === id? <br/> : <Xarrow start={id} end={d} color='white' strokeWidth='1' headSize='20' path='straight'/>
                })}
            </div>
        );
    }

}

export default Star;
