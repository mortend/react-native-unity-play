import { NativeModules, NativeEventEmitter, EventSubscription } from 'react-native'

const { UnityNativeModule } = NativeModules

export interface UnityModule {
    /**
     * Return whether is Unity ready.
     */
    isReady(): Promise<boolean>
    /**
     * Manual init the Unity. Usually Unity is auto created when the first view is added.
     */
    createUnity(): Promise<boolean>
    /**
     * Listen for messages from Unity.
     */
    addListener(onMessage: (data: any) => void): EventSubscription
    /**
     * Invoke a Unity method returning a promise.
     * @param gameObject The Name of GameObject. Also can be a path string.
     * @param methodName Method name in GameObject instance.
     * @param input An object to serialize as JSON.
     */
    callMethod(gameObject: string, methodName: string, input: any): Promise<any>
    /**
     * Send Message to Unity.
     * @param gameObject The Name of GameObject. Also can be a path string.
     * @param methodName Method name in GameObject instance.
     * @param message The message that is being sent.
     */
    sendMessage(gameObject: string, methodName: string, message: string): void
    /**
     * Pause the Unity player
     */
    pause(): void
    /**
     * Pause the Unity player
     */
    resume(): void
    /**
     * Quit the Unity player
     */
    quit(): void
}

class UnityModuleImpl implements UnityModule {
    eventEmitter: NativeEventEmitter
    methodHandle = -1

    constructor() {
        this.eventEmitter = new NativeEventEmitter(UnityNativeModule)
    }

    public async isReady() {
        return UnityNativeModule.isReady()
    }

    public async createUnity() {
        return UnityNativeModule.createUnity()
    }

    public addListener(onMessage: (data: any) => void) {
        return this.eventEmitter.addListener("message", onMessage)
    }

    public async callMethod(gameObject: string, methodName: string, input: any) {
        return new Promise((resolve, reject) => {
            const handle = ++this.methodHandle
            const onError = this.eventEmitter.addListener("reject", json => {
                const args = JSON.parse(json)
                if (args.handle === handle) {
                    onError.remove()
                    onReturn.remove()
                    reject(args.data)
                }
            })
            const onReturn = this.eventEmitter.addListener("resolve", json => {
                const args = JSON.parse(json)
                if (args.handle === handle) {
                    onError.remove()
                    onReturn.remove()
                    resolve(args.data)
                }
            })
            UnityNativeModule.sendMessage(gameObject, methodName, JSON.stringify({
                handle,
                input
            }))
        })
    }

    public sendMessage(gameObject: string, methodName: string, message: string) {
        UnityNativeModule.sendMessage(gameObject, methodName, message)
    }

    public pause() {
        UnityNativeModule.pause()
    }

    public resume() {
        UnityNativeModule.resume()
    }

    public quit() {
        UnityNativeModule.quit()
    }
}

export const UnityModule: UnityModule = new UnityModuleImpl()
