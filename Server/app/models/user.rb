class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :two_factor_authenticatable, :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  has_one_time_password

  def send_two_factor_authentication_code
    SinchSms.send('YOUR_APP_KEY', 'YOUR_APP_SECRET', "Your code is #{otp_code}", phone_number)
  end
end
