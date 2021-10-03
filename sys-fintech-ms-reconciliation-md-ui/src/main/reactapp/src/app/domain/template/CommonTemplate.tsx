import routes from '../../routeConfig';
import {
    Route,
    Link
} from "react-router-dom";

export function CommonTemplate() {
    const renderSwitches = ()=>{
        let elements: Array<any> = [];
        
        routes.forEach((domain)=>{
            domain.pages.forEach((page)=>{
                elements.push(
                    <Route key={page.path} exact path={page.path}>
                        <page.component />
                    </Route>
                );
            });
        });

        return elements;
    };
    return (
        <>
            <header>
                <nav className="navbar navbar-dark navbar-expand-md bg-dark">
                    <div className="container-fluid"><a className="navbar-brand" href="#">Fin Tech</a><button data-bs-toggle="collapse" className="navbar-toggler" data-bs-target="#navcol-1"><span className="visually-hidden">Toggle navigation</span><span className="navbar-toggler-icon"></span></button>
                        <div className="collapse navbar-collapse" id="navcol-1">
                            <ul className="navbar-nav">
                                <li className="nav-item"></li>
                                <li className="nav-item"></li>
                                <li className="nav-item"></li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>
            <div className="container-fluid">
                <div className="row mt-2">
                    <div className="col-md-3 col-lg-2 d-md-block collapse" id="navcol-1">
                        <ul className="nav nav-tabs flex-column shadow-sm">
                            <li className="nav-item"><Link className="nav-link" to={routes[0].pages[0].path}>Reconciliation Helper</Link></li>
                        </ul>
                    </div>
                    <div className="col-md-9 col-lg-10">
                        <main>
                            { renderSwitches() }
                        </main>
                    </div>
                </div>
            </div>
            <footer></footer>
        </>
        
    );
}
