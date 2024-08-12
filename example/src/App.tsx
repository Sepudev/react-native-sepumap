import { useEffect } from 'react';
import { StyleSheet, View, Alert, DeviceEventEmitter } from 'react-native';
import { SepumapView } from 'react-native-sepumap';

export default function App() {
  useEffect(() => {
    const eventListener = DeviceEventEmitter.addListener('onMarkerClick', (event) => {
      const { latitude, longitude, title } = event;
      Alert.alert(`Marker clicked`, `Title: ${title}\nLatitude: ${latitude}\nLongitude: ${longitude}`);
    });

    return () => {
      eventListener.remove();
    };
  }, []);

  const markers = [
    { latitude: 37.763007, longitude: -122.417370, title: 'Marker 1 - 659 S Van Ness Ave' },
    { latitude: 37.761722, longitude: -122.421608, title: 'Marker 2 - 18th St & Valencia St' },
    { latitude: 37.763771, longitude: -122.421793, title: 'Marker 3 - 560-594 Valencia St' },
  ];
  
  return (
    <View style={styles.container}>
      <SepumapView style={{ flex: 1}} markers={markers} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
