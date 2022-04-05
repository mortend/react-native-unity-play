import * as React from "react"
import { requireNativeComponent, NativeModules, ViewPropTypes } from "react-native"

const { RNUnity } = NativeModules

export class UnityResponderView extends React.Component {
    static propTypes = {
        ...ViewPropTypes,
    }

    componentDidMount() {
        RNUnity.initialize()
    }

    componentWillUnmount() {
        RNUnity.unloadUnity()
    }

    render() {
        const { ...props } = this.props
        return (
            <ResponderView {...props} />
        )
    }
}

// @ts-ignore
const ResponderView = requireNativeComponent("UnityResponderView", UnityResponderView)
