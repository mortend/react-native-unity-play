// @ts-ignore
import * as React from 'react';
// @ts-ignore
import { requireNativeComponent, NativeModules, ViewPropTypes } from 'react-native';
import * as PropTypes from "prop-types";

const { UnityNativeModule } = NativeModules;

export default class UnityResponderView extends React.Component {
    public static propTypes = {
        ...ViewPropTypes,
        fullScreen: PropTypes.bool
    }

    constructor(props: any) {
        super(props);
    }

    static postMessage(gameObject = '', functionName = '', message = '') {
        if (gameObject !== '' && functionName !== '' && message !== '') {
            UnityNativeModule.postMessage(gameObject, functionName, message);
        }
    }

    public componentDidMount() {
        UnityNativeModule.initialize();
    }

    public componentWillUnmount() {
        UnityNativeModule.unloadUnity();
    }

    public render() {
        // @ts-ignore
        const { ...props } = this.props;

        return (
            <ResponderView {...props} />
        );
    }
}

// @ts-ignore
const ResponderView = requireNativeComponent('UnityResponderView', UnityResponderView);
