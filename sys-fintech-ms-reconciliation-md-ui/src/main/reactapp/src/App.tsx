import {
  HashRouter as Router,
  //BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import routes from './app/routeConfig';

function App() {
  return (
    <Router>
      <Switch>
        {routes.map((domain)=>{
          return (
            <Route key={domain.path} path={domain.path}>
              <domain.component />
            </Route>
          );
        })}
      </Switch>
    </Router>
  );
}

export default App;
