import { StyleSheet, View } from 'react-native';
import { SepumapView } from 'react-native-sepumap';

export default function App() {
  return (
    <View style={styles.container}>
      <SepumapView style={styles.map} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  },
});
