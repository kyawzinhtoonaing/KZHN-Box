import { RcDomain } from './domain/rc/RcDomain';
import { RcHomePage } from './domain/rc/page/RcHomePage';

interface Page {
  path: string;
  component: any;
}

interface Domain {
  path: string;
  component: any;
  pages: Array<Page>
}

const routes: Array<Domain> = [
  {
    path: "/rc",
    component: RcDomain,
    pages: [
      {
        path: "/rc/index",
        component: RcHomePage
      }
    ]
  },
  {
    path: "/",
    component: RcDomain,
    pages: [
      {
        path: "/",
        component: RcHomePage
      }
    ]
  }
];

export default routes;