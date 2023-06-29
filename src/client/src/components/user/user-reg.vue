<template>
    <div class="user_reg">
        <form>
            <h4 class="user_reg__i">Registration</h4>
            <div class="row user_reg__i">
                <div class="col">
                  <label for="Name">Name</label>
                  <input type="text" class="form-control" id="Name" v-model="user.firstName">
                </div>
                <div class="col">
                  <label for="Lastname">Lastame</label>
                  <input type="text" class="form-control" id="Lastname" v-model="user.secondName">
                </div>
            </div>
            <div class="user_reg__i form-group">
              <label for="Email">Email address</label>
              <input type="email" class="form-control" id="Email" aria-describedby="emailHelp" placeholder="Enter email" v-model="user.email">
              <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="user_reg__i form-group">
              <label for="exampleInputPassword1">Password</label>
              <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" v-model="user.password">
            </div>
            <button type="button" @click="Regist" class="btn btn-primary" style="margin-bottom: 10px">Regist</button>
            <br>
            <RouterLink to="/login">Login</RouterLink>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'v-user-reg',
    data() {
        return {
            user: {
                firstName: '',
                secondName: '',
                email: '', 
                password: '',
            }
        }
    },
    methods: {
        async Regist() {
            try {
                const user = await axios({
                    method: 'post',
                    url: 'http://localhost:8080/online-store/v1/signup',
                    data: {
                        firstName: this.user.firstName,
                        secondName: this.user.secondName,
                        email: this.user.email, 
                        password: this.user.password,
                    },
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                    });
                alert('Регистрация прошла успешно')
                this.$router.push({name: 'login'})
            } catch (e) {
                if(e.response.status === 400) {
                    alert('Такой почты не существует!')
                }
                if(e.response.status === 409) {
                    alert('Такой пользователь уже существует!')
                }
                console.log(e)
            }
        }
    }

}
</script>

<style lang="scss" scoped>
.user_reg{
    margin: auto;
    margin-top: 270px;
    width: 500px;
    padding: 20px 40px 20px 40px;
    border: 1px solid lightgrey;
    &__i {
        margin-bottom: 20px;
    }
}
</style>