# Auth controller

## Login

```JSON
{
  "Email": "string",
  "Password": "string"
}
```

Return JWT access token

## Register

```JSON
{
    "Pesel": "string",
    "Email": "string",
    "FirstName": "string",
    "PhoneNumber": "string",
    "SurName": "string",
    "Password": "string"
}
```

## Endpoints

- [Login](#login)

```HTTP
POST /api/v1/auth/login
```

- [Register](#register)

```HTTP
POST /api/v1/auth/register
```
