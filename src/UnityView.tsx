import * as React from "react"
import type { EventSubscription, ViewProperties } from "react-native"
import { Platform, ViewPropTypes } from "react-native"
import { UnityAndroidView } from "./UnityAndroidView"
import { UnityResponderView } from "./UnityResponderView"
import { UnityModule } from "./UnityModule"
import * as PropTypes from "prop-types"

export interface UnityViewProps extends ViewProperties {
    onMessage?: (message: any) => void
}

export class UnityView extends React.Component<UnityViewProps> {
    static propTypes = {
        ...ViewPropTypes,
        onMessage: PropTypes.func
    }

    private listener?: EventSubscription

    componentDidMount() {
        if (this.props.onMessage) {
            this.listener = UnityModule.addListener(this.props.onMessage)
        }
    }

    componentWillUnmount() {
        this.listener?.remove()
    }

    render() {
        const { ...props } = this.props
        return Platform.OS === "android" ? (
            <UnityAndroidView {...props} />
        ) : (
            <UnityResponderView {...props} />
        )
    }
}
