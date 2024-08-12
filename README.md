# react-native-sepumap

React Native library map component based in Android Kotlin + TS, iOS coming soon 👀!

## Installation

```sh
npm install react-native-sepumap
```

## Requirements

You will need a google maps api key generated in GCP (Google Cloud Platform).

## Android usage
 
 First, set your google maps key in android/app/src/main/AndroidManifest.xml as follows:

 ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android">
      <application>
        <activity>
          <intent-filter>
              <action android:name="android.intent.action.MAIN" />
              <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
        </activity>
        <meta-data android:name="com.google.android.geo.API_KEY" android:value="YOUR_API_KEY_VALUE" />
      </application>
    </manifest>
```
Then, where you want to use the map import it as follows:

```js
import { SepumapView } from 'react-native-sepumap';

```
The array of markers has the following types:

```ts
interface Marker {
  latitude: number;
  longitude: number;
  title: string;
}
```

```js
 const markers = [
    { latitude: 37.763007, longitude: -122.417370, title: 'Marker 1' },
    { latitude: 37.761722, longitude: -122.421608, title: 'Marker 2' },
    { latitude: 37.763771, longitude: -122.421793, title: 'Marker 3' },
  ];

// ...
<View style={styles.container}>
  <SepumapView style={{ flex: 1}} markers={markers} />
</View>
```

It also has a listener called onMarkerClick to detect the properties of each marker and perform actions if needed. For example:

```js
useEffect(() => {
    const eventListener = DeviceEventEmitter.addListener('onMarkerClick', (event) => {
      const { latitude, longitude, title } = event;
      Alert.alert(`Marker clicked`, `Title: ${title}\nLatitude: ${latitude}\nLongitude: ${longitude}`);
    });

    return () => {
      eventListener.remove();
    };
  }, []);
```

### Example app running

![](https://res.cloudinary.com/dramvpuct/image/upload/v1723476091/exampleApp_pu9n2q.gif)

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
