export interface User {
  Id?: number;
  Email: String;
  Password?: String;
  FirstName?: String;
  SurName?: String;
  Roles?: Roles;
  Pesel?: String;
};

interface Roles {
  Admin?: boolean;
  User?: boolean;
  Staff?: boolean;
}
