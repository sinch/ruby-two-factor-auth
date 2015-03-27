class VerificationsController < ApplicationController
	skip_before_filter :verify_authenticity_token

	def generate_code
		phone_number = params["phone_number"]
		code = Random.rand(10000..99999).to_s

		Verification.create(phone_number: phone_number, code: code)
		SinchSms.send('YOUR_APP_KEY', 'YOUR_APP_SECRET', "Your code is #{code}", phone_number)

		render status: 200, nothing: true
	end

	def verify_code
		phone_number = params["phone_number"]
		code = params["code"]
		verification = Verification.where(phone_number: phone_number, code: code).first

		if verification
		  verification.destroy
			render status: 200, json: {verified: true}.to_json
		else
			render status: 200, json: {verified: false}.to_json
		end
	end
end
